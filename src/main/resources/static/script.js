document.getElementById("ledForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const data = {
        color: document.getElementById("color").value,
        brightness: parseInt(document.getElementById("brightness").value),
        mode: document.getElementById("mode").value,
        speed: parseInt(document.getElementById("speed").value)
    };

    try {
        const response = await fetch("/led/changeLedConfig", {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });

        const text = await response.text();
        document.getElementById("result").innerText = text;
    } catch (error) {
        document.getElementById("result").innerText = "Connection error";
    }
});
