const express = require("express");
const cors = require("cors");
const matchingRoutes = require("./routes/matching");

const app = express();
const PORT = process.env.PORT || 5000;

app.use(cors());
app.use(express.json());

app.use("/api/matching", matchingRoutes);

app.get("/", (req, res) => {
  res.json({
    success: true,
    message: "SkillBridge Backend API is running"
  });
});

app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});