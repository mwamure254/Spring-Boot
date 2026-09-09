function togglePassword(fieldId, button) {
    const input = document.getElementById(fieldId);
    const icon = button.querySelector("i");

    if (input.type === "password") {
        input.type = "text";
        icon.classList.remove("bi-eye");
        icon.classList.add("bi-eye-slash");
    } else {
        input.type = "password";
        icon.classList.remove("bi-eye-slash");
        icon.classList.add("bi-eye");
    }
}

document.addEventListener("DOMContentLoaded", function () {
    const alert = document.getElementById("floatingAlert");

    if (alert) {
        setTimeout(() => {
            const bsAlert = bootstrap.Alert.getOrCreateInstance(alert);
            bsAlert.close();
        }, 5000);
    }
});

// PRODUCT SEARCH
document.getElementById("productSearch")
    .addEventListener("keyup", function () {

        let search = this.value.toLowerCase();

        document
            .querySelectorAll("#productTable tbody tr")
            .forEach(function (row) {

                let text = row.innerText.toLowerCase();

                row.style.display =
                    text.includes(search)
                        ? ""
                        : "none";

            });

    });