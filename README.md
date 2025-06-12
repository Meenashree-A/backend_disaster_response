# backend_disaster_response
🌍 Disaster Response Management System
A Spring Boot + AWS-integrated web application to help disaster victims report needs (like food, water, medicine) and allow admin users to view and manage these needs.

🚀 Features
✅ Disaster Report Form (User Side)

Auto-detect location using OpenStreetMap (Leaflet.js)

Submit type, description, and upload photo

Stores data in AWS DynamoDB, file in AWS S3, and sends notification via AWS SNS

✅ Admin Page

Retrieve and view all reported needs from DynamoDB

Login protection (to be implemented for security)

🏗️ Technologies Used
Spring Boot

AWS S3, DynamoDB, SNS

OpenStreetMap (Leaflet.js)

HTML5, CSS3, JavaScript

Maven
