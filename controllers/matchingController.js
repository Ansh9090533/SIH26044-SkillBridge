const opportunities = require("../data/opportunities");
const { rankJobs } = require("../services/matchingService");

const matchJobs = (req, res) => {
  try {
    const { studentSkills, limit = opportunities.length } = req.body;

    if (!Array.isArray(studentSkills)) {
      return res.status(400).json({
        success: false,
        message: "studentSkills must be an array."
      });
    }

    const rankedJobs = rankJobs(studentSkills, opportunities);

    return res.status(200).json({
      success: true,
      data: {
        totalOpportunities: opportunities.length,
        results: rankedJobs.slice(0, Number(limit))
      }
    });
  } catch (error) {
    console.error("Job matching error:", error);
    return res.status(500).json({
      success: false,
      message: "Failed to match jobs."
    });
  }
};

module.exports = { matchJobs };