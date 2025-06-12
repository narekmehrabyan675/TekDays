<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>Step 3 - Confirm</title>
    <style>
    input, li , p{
        margin-top: 10px;
    }

    </style>
</head>
<body>

<h2 style="text-align: center">Step 3: Confirm Your Details</h2>

<div class="container" >
    <div style="display: flex;justify-content: center;">

        <ul >
    <li><strong>Full Name:</strong> ${data.fullName}</li>
    <li><strong>Username:</strong> ${data.userName}</li>
    <li><strong>Password:</strong> ******</li>
    <li><strong>Website:</strong> ${data.website}</li>
    <li><strong>Bio:</strong> ${data.bio}</li>
    <li><strong>Email:</strong> ${data.email}</li>
</ul>
</div>
</div>

<div class="container">
    <div style="display: flex;justify-content: center;">
<p>
<g:form action="complete">
    <g:submitButton name="submit" value="Submit Registration" />
</g:form>
</p>
        </div>
</div>

<p><g:link action="step2">Back to Step 2</g:link></p>
</body>
</html>
