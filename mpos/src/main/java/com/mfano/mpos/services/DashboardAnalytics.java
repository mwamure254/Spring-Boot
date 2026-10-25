package com.mfano.mpos.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mfano.mpos.dtos.BudgetChart;
import com.mfano.mpos.dtos.OrderStatus;
import com.mfano.mpos.dtos.PaymentMethodReport;
import com.mfano.mpos.dtos.ReportsChart;
import com.mfano.mpos.dtos.TopSellingProduct;
import com.mfano.mpos.order.Order;
import com.mfano.mpos.order.OrderItemRepository;
import com.mfano.mpos.order.OrderRepository;
import com.mfano.mpos.repositories.ProductRepository;

@Service
@Transactional(readOnly = true)
public class DashboardAnalytics {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    public DashboardAnalytics(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
    }

    public long getSalesCount(LocalDate startDate, LocalDate endDate) {
        return getCompletedOrders(startDate, endDate).size();
    }

    public BigDecimal getRevenue(LocalDate startDate, LocalDate endDate) {
        return getCompletedOrders(startDate, endDate)
                .stream()
                .map(Order::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<Order> getCompletedOrders(
            LocalDate startDate, LocalDate endDate) {

        return orderRepository.findOrdersForAnalytics(
                startDate.atStartOfDay(),
                endDate.atStartOfDay(),
                OrderStatus.COMPLETED);
    }

    public long getTodaySales() {
        LocalDate today = LocalDate.now();

        return orderRepository.countSalesBetween(
                today.atStartOfDay(),
                today.plusDays(1).atStartOfDay(),
                OrderStatus.COMPLETED);
    }

    public long getTodayItemsSold(LocalDate today) {
        //LocalDate today = LocalDate.now();

        return orderRepository.countItemsBetween(
                today.atStartOfDay(),
                today.plusDays(1).atStartOfDay(),
                OrderStatus.COMPLETED);
    }

    public BigDecimal getTodayRevenue() {
        LocalDate today = LocalDate.now();

        return orderRepository.sumRevenueBetween(
                today.atStartOfDay(),
                today.plusDays(1).atStartOfDay(),
                OrderStatus.COMPLETED);
    }

    public BigDecimal getMonthlyRevenue() {
        LocalDate today = LocalDate.now();
        LocalDate start = today.withDayOfMonth(1);

        return orderRepository.sumRevenueBetween(
                start.atStartOfDay(),
                start.plusMonths(1).atStartOfDay(),
                OrderStatus.COMPLETED);
    }

    public BigDecimal getYearlyRevenue() {
        LocalDate today = LocalDate.now();
        LocalDate start = today.withDayOfYear(1);

        return orderRepository.sumRevenueBetween(
                start.atStartOfDay(),
                start.plusYears(1).atStartOfDay(),
                OrderStatus.COMPLETED);
    }

    public List<TopSellingProduct> getTopSellingProducts() {
        LocalDate today = LocalDate.now();

        LocalDateTime startDate = today
                .withDayOfMonth(1)
                .atStartOfDay();

        LocalDateTime endDate = today
                .withDayOfMonth(1)
                .plusMonths(1)
                .atStartOfDay();

        return orderItemRepository.findTop10SellingProducts(
                startDate,
                endDate,
                OrderStatus.COMPLETED);
    }

    public List<Order> getRecentSales() {
        return orderRepository.findTop10ByOrderByCreatedAtDesc();
    }

    public List<BudgetChart> getBudgetChartData() {
        return orderItemRepository.findAll()
                .stream()
                .map(budget -> new BudgetChart(
                        budget.getOrder().getPm(),
                        budget.getUnitPrice(),
                        budget.getSubtotal()))
                .toList();
    }

    public List<ReportsChart> getReportsChartData() {

        List<Order> orders = orderRepository.findAll();
        Map<LocalDate, List<Order>> grouped = new TreeMap<>();

        for (Order order : orders) {
            // Assumes Order inherits getCreatedAt()
            // from its common base entity.
            LocalDate date = order.getCreatedAt().toLocalDate();

            grouped.computeIfAbsent(date, k -> new ArrayList<>())
                    .add(order);
        }

        List<ReportsChart> result = new ArrayList<>();

        grouped.forEach((date, dailyOrders) -> {

            long sales = getTodayItemsSold(date);

            BigDecimal revenue = dailyOrders.stream()
                    .map(Order::getTotal)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // customer relationship in your Order entity.
            long customers = dailyOrders.stream()
                    .map(Order::getPm)
                    .filter(Objects::nonNull)
                    .distinct()
                    .count();

            result.add(new ReportsChart(
                    date,
                    sales,
                    revenue,
                    customers));
        });

        return result;
    }

    public List<PaymentMethodReport> getSalesAndRevenueByPaymentMethod() {
        return orderRepository.findSalesAndRevenueByPaymentMethod();
    }

    public long getInStockProductsCount() {
        return productRepository.countByActiveTrueAndStockQuantityGreaterThan(0);
    }

    public long getTotalInStockQuantity() {
        return productRepository.sumInStockQuantity();
    }

    public long getTotalInStockQuantityByBranch(Long branchId) {
        return productRepository.sumInStockQuantityByBranch(branchId);
    }
}