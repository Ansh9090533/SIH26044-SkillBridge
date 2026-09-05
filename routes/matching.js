/**
 * backend/routes/matching.js
 */

const express = require("express");

const {
  skillGap,
  matchJobs
} = require("../controllers/matchingController");

const router = express.Router();

/**
 * POST /api/matching/skill-gap
 */
router.post("/skill-gap", skillGap);

/**
 * POST /api/matching/match-jobs
 */
router.post("/match-jobs", matchJobs);

module.exports = router;