const express = require("express");
const matchingRoutes = require("./routes/matching");

const app = express();
const PORT = process.env.PORT || 5000;

// Middleware
app.use(express.json());

// Routes
app.use("/api/matching", matchingRoutes);

// Health check
app.get("/", (req, res) => {
    res.json({
        success: true,
        message: "SkillBridge Backend API is running"
    });
});

// Error handler
app.use((err, req, res, next) => {
    console.error(err);
    res.status(500).json({
        success: false,
        message: "Internal server error"
    });
});

app.listen(PORT, () => {
    console.log(`Server running on http://localhost:${PORT}`);
});