/**
 * backend/data/opportunities.js
 * Centralized dummy database for internships and jobs (Expanded with Cloud/DevOps and AI/ML).
 */
const opportunities = [
  {
    id: 1,
    title: "Backend Developer Intern",
    company: "TechNova",
    type: "Internship",
    location: "Remote",
    requiredSkills: ["Node.js", "Express", "MongoDB", "REST API", "Git"]
  },
  {
    id: 2,
    title: "Frontend Developer Intern",
    company: "WebWorks",
    type: "Internship",
    location: "Bangalore",
    requiredSkills: ["HTML", "CSS", "JavaScript", "React", "Git"]
  },
  {
    id: 3,
    title: "Python Developer Intern",
    company: "DataSoft",
    type: "Internship",
    location: "Remote",
    requiredSkills: ["Python", "Django", "SQL", "REST API", "Git"]
  },
  {
    id: 4,
    title: "Full Stack Developer",
    company: "InnovateX",
    type: "Job",
    location: "Delhi",
    requiredSkills: ["JavaScript", "React", "Node.js", "Express", "MongoDB", "Git"]
  },
  {
    id: 5,
    title: "Data Analyst Intern",
    company: "AnalyticsHub",
    type: "Internship",
    location: "Mumbai",
    requiredSkills: ["Python", "SQL", "Excel", "Statistics", "Power BI"]
  },
  {
    id: 6,
    title: "Cloud & DevOps Intern",
    company: "CloudScale",
    type: "Internship",
    location: "Remote",
    requiredSkills: ["AWS", "Docker", "Kubernetes", "Linux", "CI/CD", "Git"]
  },
  {
    id: 7,
    title: "AI / ML Engineer Intern",
    company: "NeuralTech",
    type: "Internship",
    location: "Hyderabad",
    requiredSkills: ["Python", "Machine Learning", "TensorFlow", "Pandas", "SQL"]
  }
];

module.exports = opportunities;