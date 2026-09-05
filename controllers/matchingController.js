/**
 * backend/controllers/matchingController.js
 */

const {
  calculateSkillGap,
  rankJobs
} = require("../services/matchingService");

/**
 * Sample opportunity data.
 *
 * This can later be replaced with:
 * MongoDB / PostgreSQL / MySQL / another API.
 */
const opportunities = [
  {
    id: 1,
    title: "Backend Developer Intern",
    company: "TechNova",
    type: "Internship",
    location: "Remote",
    requiredSkills: [
      "Node.js",
      "Express",
      "MongoDB",
      "REST API",
      "Git"
    ]
  },
  {
    id: 2,
    title: "Frontend Developer Intern",
    company: "WebWorks",
    type: "Internship",
    location: "Bangalore",
    requiredSkills: [
      "HTML",
      "CSS",
      "JavaScript",
      "React",
      "Git"
    ]
  },
  {
    id: 3,
    title: "Python Developer Intern",
    company: "DataSoft",
    type: "Internship",
    location: "Remote",
    requiredSkills: [
      "Python",
      "Django",
      "SQL",
      "REST API",
      "Git"
    ]
  },
  {
    id: 4,
    title: "Full Stack Developer",
    company: "InnovateX",
    type: "Job",
    location: "Delhi",
    requiredSkills: [
      "JavaScript",
      "React",
      "Node.js",
      "Express",
      "MongoDB",
      "Git"
    ]
  },
  {
    id: 5,
    title: "Data Analyst Intern",
    company: "AnalyticsHub",
    type: "Internship",
    location: "Mumbai",
    requiredSkills: [
      "Python",
      "SQL",
      "Excel",
      "Statistics",
      "Power BI"
    ]
  }
];

/**
 * POST /api/matching/skill-gap
 *
 * Request body:
 * {
 *   "studentSkills": ["JavaScript", "Node.js"],
 *   "requiredSkills": ["JavaScript", "Node.js", "React"]
 * }
 */
const skillGap = (req, res) => {
  try {
    const { studentSkills, requiredSkills } = req.body;

    // Validate student skills
    if (!Array.isArray(studentSkills)) {
      return res.status(400).json({
        success: false,
        message: "studentSkills must be an array."
      });
    }

    // Validate required job skills
    if (!Array.isArray(requiredSkills)) {
      return res.status(400).json({
        success: false,
        message: "requiredSkills must be an array."
      });
    }

    const result = calculateSkillGap(
      studentSkills,
      requiredSkills
    );

    return res.status(200).json({
      success: true,
      data: {
        studentSkills,
        requiredSkills,
        matchingSkills: result.matchingSkills,
        missingSkills: result.missingSkills,
        matchPercentage: result.matchPercentage
      }
    });
  } catch (error) {
    console.error("Skill gap analysis error:", error);

    return res.status(500).json({
      success: false,
      message: "Failed to analyze skill gap."
    });
  }
};

/**
 * POST /api/matching/match-jobs
 *
 * Request body:
 * {
 *   "studentSkills": ["JavaScript", "Node.js", "MongoDB"],
 *   "limit": 5
 * }
 */
const matchJobs = (req, res) => {
  try {
    const {
      studentSkills,
      limit = opportunities.length
    } = req.body;

    // Validate student skills
    if (!Array.isArray(studentSkills)) {
      return res.status(400).json({
        success: false,
        message: "studentSkills must be an array."
      });
    }

    // Validate limit
    const parsedLimit = Number(limit);

    if (
      !Number.isInteger(parsedLimit) ||
      parsedLimit <= 0
    ) {
      return res.status(400).json({
        success: false,
        message: "limit must be a positive integer."
      });
    }

    const rankedJobs = rankJobs(
      studentSkills,
      opportunities
    );

    return res.status(200).json({
      success: true,
      data: {
        totalOpportunities: opportunities.length,
        results: rankedJobs.slice(0, parsedLimit)
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

module.exports = {
  skillGap,
  matchJobs
};