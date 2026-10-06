// ==========================================
// Load Orders when page opens
// ==========================================

document.addEventListener("DOMContentLoaded", function () {
    loadOrders();
});


// ==========================================
// Get all orders from Spring Boot
// ==========================================

function loadOrders() {

    fetch("/api/orders")
        .then(response => {

            if (!response.ok) {
                throw new Error("Failed to load orders");
            }

            return response.json();
        })

        .then(orders => {

            displayOrders(orders);
            updateDashboard(orders);

        })

        .catch(error => {

            console.error("Error loading orders:", error);

            alert("Unable to load orders. Make sure Spring Boot is running.");

        });
}


// ==========================================
// Display orders in table
// ==========================================

function displayOrders(orders) {

    const tableBody =
        document.getElementById("ordersTableBody");

    tableBody.innerHTML = "";


    orders.forEach(order => {

        const row = document.createElement("tr");


        row.innerHTML = `

            <td>${order.id}</td>

            <td>${order.customerId}</td>

            <td>${formatDate(order.dateOfPurchase)}</td>

            <td>₹${order.total}</td>

            <td>${order.paymentMode}</td>

            <td>${order.orderStatus}</td>

            <td>${order.shopId}</td>

            <td>

                <button
                    onclick="viewOrder(${order.id})">
                    View
                </button>

                <button
                    onclick="deleteOrder(${order.id})">
                    Delete
                </button>

            </td>
        `;


        tableBody.appendChild(row);

    });
}


// ==========================================
// Dashboard statistics
// ==========================================

function updateDashboard(orders) {

    // Total orders

    document.getElementById("totalOrders").textContent =
        orders.length;


    // Confirmed orders

    const confirmed =
        orders.filter(
            order => order.orderStatus === "CONFIRMED"
        ).length;

    document.getElementById("confirmedOrders").textContent =
        confirmed;


    // Pending orders

    const pending =
        orders.filter(
            order => order.orderStatus === "PENDING"
        ).length;

    document.getElementById("pendingOrders").textContent =
        pending;


    // Total revenue

    const revenue =
        orders.reduce(
            (sum, order) => sum + Number(order.total),
            0
        );

    document.getElementById("totalRevenue").textContent =
        "₹" + revenue;
}


// ==========================================
// Format date
// ==========================================

function formatDate(dateString) {

    if (!dateString) {
        return "-";
    }

    const date =
        new Date(dateString);

    return date.toLocaleString();

}


// ==========================================
// View Order
// ==========================================

function viewOrder(id) {

    fetch(`/api/orders/${id}`)

        .then(response => {

            if (!response.ok) {
                throw new Error("Order not found");
            }

            return response.json();

        })

        .then(order => {

            alert(

                "Order Details\n\n" +

                "Order ID: " + order.id + "\n" +

                "Customer ID: " + order.customerId + "\n" +

                "Total: ₹" + order.total + "\n" +

                "Payment: " + order.paymentMode + "\n" +

                "Status: " + order.orderStatus + "\n" +

                "Shop ID: " + order.shopId

            );

        })

        .catch(error => {

            alert("Unable to find order.");

            console.error(error);

        });
}


// ==========================================
// Delete Order
// ==========================================

function deleteOrder(id) {

    const confirmation =
        confirm(
            "Are you sure you want to delete Order #" + id + "?"
        );


    if (!confirmation) {
        return;
    }


    fetch(`/api/orders/${id}`, {

        method: "DELETE"

    })

        .then(response => {

            if (!response.ok) {
                throw new Error("Delete failed");
            }

            return response.text();

        })

        .then(message => {

            alert(message);

            loadOrders();

        })

        .catch(error => {

            console.error("Error deleting order:", error);

            alert("Unable to delete order.");

        });
}


// ==========================================
// Show Create Order Form
// ==========================================

function showCreateOrderForm() {

    document.getElementById(
        "orderFormSection"
    ).style.display = "block";

}


// ==========================================
// Hide Create Order Form
// ==========================================

function hideCreateOrderForm() {

    document.getElementById(
        "orderFormSection"
    ).style.display = "none";

}
// ==========================================
// Create New Order
// ==========================================

document.getElementById("orderForm").addEventListener("submit", function(event) {

    event.preventDefault();

    const order = {

        customerId: Number(
            document.getElementById("customerId").value
        ),

        dateOfPurchase:
            document.getElementById("dateOfPurchase").value,

        total: Number(
            document.getElementById("total").value
        ),

        paymentMode:
            document.getElementById("paymentMode").value,

        orderStatus:
            document.getElementById("orderStatus").value,

        shopId: Number(
            document.getElementById("shopId").value
        )
    };


    fetch("/api/orders", {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(order)

    })

    .then(response => {

        if (!response.ok) {
            return response.json().then(error => {
                throw new Error(
                    error.message || "Failed to create order"
                );
            });
        }

        return response.json();

    })

    .then(createdOrder => {

        alert(
            "Order created successfully!\n\n" +
            "Order ID: " + createdOrder.id
        );

        document.getElementById("orderForm").reset();

        hideCreateOrderForm();

        loadOrders();

    })

    .catch(error => {

        console.error("Error creating order:", error);

        alert(
            "Unable to create order.\n\n" +
            error.message
        );

    });

});