// Function to open IndexedDB and initialize the object store
async function openDatabase() {
    const dbRequest = indexedDB.open("ProductDB", 1);

    return new Promise((resolve, reject) => {
        dbRequest.onupgradeneeded = (event) => {
            const db = event.target.result;
            const objectStore = db.createObjectStore("products", { keyPath: "id" });
            objectStore.createIndex("text", "text", { unique: false });
        };

        dbRequest.onsuccess = (event) => {
            resolve(event.target.result);
        };

        dbRequest.onerror = (event) => {
            reject(`Database error: ${event.target.errorCode}`);
        };
    });
}

// Function to add product to IndexedDB
function addProductToDB(db, product) {
    const transaction = db.transaction(["products"], "readwrite");
    const objectStore = transaction.objectStore("products");
    objectStore.put(product);
}

// Function to get all products from IndexedDB
function getAllProductsFromDB(db) {
    return new Promise((resolve) => {
        const transaction = db.transaction(["products"], "readonly");
        const objectStore = transaction.objectStore("products");
        const request = objectStore.getAll();

        request.onsuccess = () => {
            resolve(request.result);
        };
    });
}

// Function to calculate total amount
function calculateTotal(products) {
    return products.reduce((total, item) => total + (item.price * item.quantity), 0);
}

// Function to display checkout modal
function displayCheckout(products) {
    const totalAmount = calculateTotal(products);
    const checkoutModal = document.createElement("div");
    checkoutModal.innerHTML = `
        <div id="checkoutModal" style="position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0, 0, 0, 0.7);">
            <div style="background-color: white; margin: 20% auto; padding: 20px; width: 300px;">
                <h3>Checkout</h3>
                <ul>
                    ${products.map(item => `<li>${item.text} (Quantity: ${item.quantity}, Price: ₹${item.price.toFixed(2)})</li>`).join('')}
                </ul>
                <p>Total Amount: ₹${totalAmount.toFixed(2)}</p>
                <button id="payButton">Pay</button>
                <button id="closeModal">Close</button>
            </div>
        </div>
    `;
    document.body.appendChild(checkoutModal);

    // Add event listeners for buttons
    document.getElementById("payButton").onclick = () => {
        // Redirect to Razorpay payment gateway (replace with your Razorpay logic)
        console.log("Redirecting to Razorpay...");
        // Example: Razorpay logic goes here
    };

    document.getElementById("closeModal").onclick = () => {
        document.body.removeChild(checkoutModal);
    };
}

// Main application logic
document.addEventListener("DOMContentLoaded", async (e) => {
    const htmlQrCode = new Html5Qrcode("qr-code-reader");
    const cameraSelect = document.getElementById("camera-select");
    const list = document.getElementById("list");
    const checkoutButton = document.createElement("button");
    checkoutButton.textContent = "Checkout";
    document.body.appendChild(checkoutButton);

    const beepSound = new Audio('beep.wav');

    // Open the IndexedDB
    const db = await openDatabase();
    const items = await getAllProductsFromDB(db);

    // Load existing products into the list
    items.forEach(item => {
        const l = document.createElement("li");
        l.textContent = `${item.text} (Quantity: ${item.quantity})`;
        list.appendChild(l);
    });

    try {
        const cameras = await Html5Qrcode.getCameras();
        
        if (cameras && cameras.length) {
            cameras.forEach((camera, index) => {
                const option = document.createElement("option");
                option.value = camera.id;
                option.text = camera.label || `Camera ${index + 1}`;
                cameraSelect.appendChild(option);
            });

            cameraSelect.addEventListener("change", async () => {
                const selectedCameraId = cameraSelect.value;
                
                try {
                    await htmlQrCode.start(
                        selectedCameraId,
                        {
                            fps: 1,
                            qrbox: { height: 250, width: 300 }
                        },
                        async (decodedText) => {
                            console.log(`QR code scanned: ${decodedText}`);
                            beepSound.play();

                            // Check for duplicates and update the list accordingly
                            const existingItem = items.find(item => item.text === decodedText);
                            
                            if (existingItem) {
                                existingItem.quantity++;
                            } else {
                                items.push({ id: decodedText, text: decodedText, quantity: 1, price: 100 }); // Set price as needed
                            }

                            // Clear the list before displaying updated items
                            list.innerHTML = '';

                            // Update the list display
                            items.forEach(item => {
                                const l = document.createElement("li");
                                l.textContent = `${item.text} (Quantity: ${item.quantity})`;
                                list.appendChild(l);
                                // Save product to IndexedDB
                                addProductToDB(db, item);
                            });

                            // Pause scanning for 1 second
                            await new Promise(resolve => setTimeout(resolve, 1000));
                        },
                        (errorMessage) => {
                            console.warn(`QR code scanning error: ${errorMessage}`);
                        }
                    );
                } catch (err) {
                    console.error(`Unable to start scanning: ${err}`);
                }
            });
        }
    } catch (err) {
        console.error(`Error fetching cameras: ${err}`);
    }

    // Checkout button logic
    checkoutButton.onclick = () => {
        displayCheckout(items);
    };
});
