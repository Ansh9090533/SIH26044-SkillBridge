const http = require('http');

function sendPostRequest(path, data) {
    return new Promise((resolve, reject) => {
        const dataString = JSON.stringify(data);
        const options = {
            hostname: 'localhost',
            port: 5000,
            path: path,
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Content-Length': Buffer.byteLength(dataString)
            }
        };

        const req = http.request(options, (res) => {
            let responseData = '';
            res.on('data', (chunk) => { responseData += chunk; });
            res.on('end', () => {
                resolve({ statusCode: res.statusCode, body: JSON.parse(responseData) });
            });
        });

        req.on('error', reject);
        req.write(dataString);
        req.end();
    });
}

async function runTests() {
    console.log("Testing SkillBridge APIs...\n");

    try {
        // 1. Test Skill Gap Analysis
        console.log("1. Testing POST /api/matching/skill-gap...");
        const gapResponse = await sendPostRequest('/api/matching/skill-gap', {
            studentSkills: ["JavaScript", "Node.js", "MongoDB", "Git"],
            requiredSkills: ["Node.js", "Express", "MongoDB", "REST API", "Git"]
        });
        console.log("Status:", gapResponse.statusCode);
        console.log("Response:", JSON.stringify(gapResponse.body, null, 2));

        console.log("\n-------------------------------------------\n");

        // 2. Test Job Matching
        console.log("2. Testing POST /api/matching/match-jobs...");
        const jobResponse = await sendPostRequest('/api/matching/match-jobs', {
            studentSkills: ["JavaScript", "Node.js", "Express", "MongoDB", "Git"],
            limit: 3
        });
        console.log("Status:", jobResponse.statusCode);
        console.log("Response:", JSON.stringify(jobResponse.body, null, 2));

    } catch (error) {
        console.error("Test failed, make sure your server is running with 'node server.js':", error.message);
    }
}

runTests();