// ===============================
// SkillBridge Student Dashboard
// Temporary frontend functionality
// ===============================


// ---------- DEMO STUDENT DATA ----------

const studentData = {
    name: "Student Name",
    careerGoal: "Java Backend Developer",

    overallScore: 72,

    skillsAssessed: 6,

    weakSkills: 2,

    jobMatches: 8,

    skills: {
        Java: 80,
        SQL: 60,
        DBMS: 75,
        DSA: 55
    },

    targetedTests: {
        DSA: 78,
        SQL: null
    },

    opportunities: [
        {
            title: "Java Backend Intern",
            company: "Example Company",
            location: "Remote",
            match: 87
        },
        {
            title: "Software Developer Intern",
            company: "Example Company",
            location: "Bangalore",
            match: 81
        },
        {
            title: "Backend Developer Intern",
            company: "Example Company",
            location: "Hybrid",
            match: 76
        }
    ]
};


// ---------- LOAD DASHBOARD DATA ----------

function loadDashboard() {

    // Overall score
    const overallScore =
        document.querySelector(".summary-card:nth-child(1) h2");

    if (overallScore) {
        overallScore.textContent =
            studentData.overallScore + "%";
    }


    // Skills assessed
    const skillsAssessed =
        document.querySelector(".summary-card:nth-child(2) h2");

    if (skillsAssessed) {
        skillsAssessed.textContent =
            studentData.skillsAssessed;
    }


    // Weak skills
    const weakSkills =
        document.querySelector(".summary-card:nth-child(3) h2");

    if (weakSkills) {
        weakSkills.textContent =
            studentData.weakSkills;
    }


    // Job matches
    const jobMatches =
        document.querySelector(".summary-card:nth-child(4) h2");

    if (jobMatches) {
        jobMatches.textContent =
            studentData.jobMatches;
    }
}


// ---------- TAKE ASSESSMENT ----------

const assessmentButton =
    document.querySelector(".primary-btn");

if (assessmentButton) {

    assessmentButton.addEventListener("click", function () {

        alert(
            "Assessment module will open here.\n\n" +
            "Later this button will call the Spring Boot API."
        );

    });

}


// ---------- VIEW RESULT ----------

const resultButton =
    document.querySelector(".secondary-btn");

if (resultButton) {

    resultButton.addEventListener("click", function () {

        alert(
            "Assessment Result\n\n" +
            "Overall Score: " +
            studentData.overallScore +
            "%"
        );

    });

}



// ---------- LOAD DATA ----------

loadDashboard();


// ---------- CONSOLE MESSAGE ----------

console.log("SkillBridge Dashboard Loaded Successfully.");