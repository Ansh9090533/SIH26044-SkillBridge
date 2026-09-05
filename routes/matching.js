const express = require("express");
const { skillGap } = require("../controllers/matchingController");

const router = express.Router();

// POST /api/matching/skill-gap
router.post("/skill-gap", skillGap);

module.exports = router;