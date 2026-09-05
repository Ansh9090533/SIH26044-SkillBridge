const normalizeSkill = (skill) => {
  if (typeof skill !== "string") return "";
  return skill.trim().toLowerCase();
};

const calculateSkillGap = (studentSkills, requiredSkills) => {
  const studentSkillSet = new Set(studentSkills.map(normalizeSkill));
  const matchingSkills = [];
  const missingSkills = [];

  for (const skill of requiredSkills) {
    if (studentSkillSet.has(normalizeSkill(skill))) {
      matchingSkills.push(skill);
    } else {
      missingSkills.push(skill);
    }
  }

  const matchPercentage = requiredSkills.length === 0 
    ? 0 
    : Math.round((matchingSkills.length / requiredSkills.length) * 100);

  return {
    matchingSkills,
    missingSkills,
    matchPercentage
  };
};

module.exports = { calculateSkillGap };