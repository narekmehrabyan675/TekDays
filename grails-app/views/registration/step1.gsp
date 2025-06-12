<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <title>Step 1 - Personal Info</title>
    <style>
    input, textarea {
        margin-top: 10px;
    }

    </style>
</head>



<body>
<h2 style="text-align: center">Step 1: Personal Info</h2>

<div style="display: flex;justify-content: center;  margin-top: 10px">
    <g:if test="${flash.message}">
        <div class="error">${flash.message}</div>
    </g:if>
</div>

<div class="container">
    <div style="display: flex;justify-content: center;">


        <g:form action="step2">
            <label>Full Name:</label>
            <g:textField name="fullName" value="${session.registrationData?.fullName}"/><br>
            <label>Username:</label>
            <g:textField name="userName" value="${session.registrationData?.userName}"/><br>
            <label>Password:</label>
            <g:passwordField name="password"/><br>
            <label>Website:</label>
            <g:textField name="website" value="${session.registrationData?.website}"/><br>
            <label>Bio:</label>
            <g:textArea name="bio">${session.registrationData?.bio}</g:textArea><br>
            <g:submitButton name="next" value="Next"/>
        </g:form>
    </div>
</div>
</body>
</html>
