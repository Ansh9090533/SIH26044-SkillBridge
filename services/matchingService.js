/**
 * backend/services/matchingService.js
 *
 * Core business logic for SkillBridge matching.
 */

/**
 * Normalize a skill for case-insensitive comparison.
 *
 * Example:
 * "Node.js" -> "node.js"
 * "  JavaScript " -> "javascript"
 */
const normalizeSkill = (skill) => {
  if (typeof skill !== "string") {
    return "";
  }

  return skill.trim().toLowerCase();
};

/**
 * Remove duplicate skills while preserving the original
 * display value.
 */
const uniqueSkills = (skills) => {
  const seen = new Set();
  const result = [];

  for (const skill of skills) {
    const normalized = normalizeSkill(skill);

    if (normalized && !seen.has(normalized)) {
      seen.add(normalized);
      result.push(skill.trim());
    }
  }

  return result;
};

/**
 * Analyze the skill gap between a student and a job.
 *
 * Match percentage is calculated as:
 *
 * (matching required skills / total required skills) * 100
 *
 * Example:
 * Student:  ["JavaScript", "Node.js", "MongoDB"]
 * Required: ["JavaScript", "Node.js", "MongoDB", "React"]
 *
 * Matching = 3
 * Required = 4
 * Score = 75%
 */
const calculateSkillGap = (studentSkills, requiredSkills) => {
  const students = uniqueSkills(studentSkills);
  const required = uniqueSkills(requiredSkills);

  const studentSkillSet = new Set(
    students.map(normalizeSkill)
  );

  const matchingSkills = [];
  const missingSkills = [];

  for (const requiredSkill of required) {
    if (studentSkillSet.has(normalizeSkill(requiredSkill))) {
      matchingSkills.push(requiredSkill);
    } else {
      missingSkills.push(requiredSkill);
    }
  }

  const matchPercentage =
    required.length === 0
      ? 0
      : Math.round(
          (matchingSkills.length / required.length) * 100
        );

  return {
    matchingSkills,
    missingSkills,
    matchPercentage
  };
};

/**
 * Rank jobs according to how well they match
 * the student's skills.
 *
 * Expected opportunity format:
 *
 * {
 *   id: 1,
 *   title: "Backend Developer",
 *   company: "ABC",
 *   requiredSkills: ["Node.js", "Express", "MongoDB"]
 * }
 */
const rankJobs = (studentSkills, opportunities) => {
  const rankedJobs = opportunities.map((job) => {
    const analysis = calculateSkillGap(
      studentSkills,
      job.requiredSkills || []
    );

    return {
      ...job,
      matchingSkills: analysis.matchingSkills,
      missingSkills: analysis.missingSkills,
      matchPercentage: analysis.matchPercentage
    };
  });

  // Highest match first.
  // If scores are equal, preserve the original order.
  rankedJobs.sort(
    (a, b) => b.matchPercentage - a.matchPercentage
  );

  return rankedJobs;
};

module.exports = {
  normalizeSkill,
  calculateSkillGap,
  rankJobs
};