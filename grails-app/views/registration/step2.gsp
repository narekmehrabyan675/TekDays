<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <title>Step 2 - Email</title>

    <style>
    input, textarea {
        margin-top: 10px;
    }

    </style>
</head>

<body>
<h2  style="text-align: center">Step 2: Email</h2>

<div style="display: flex;justify-content: center;  margin-top: 10px">
    <g:if test="${flash.message}">
        <div class="error">${flash.message}</div>
    </g:if>
</div>

<div class="container">
    <div style="display: flex;justify-content: center;">
        <g:form action="step3">
            <p>
                <label>Email:</label>
                <g:textField name="email" value="${session.registrationData?.email}"/>
            </p>

            <p>
                <g:submitButton name="next" value="Next "/>
            </p>
        </g:form>


    </div>

    <p><g:link action="step1"> Back to Step 1</g:link></p>

</div>
</body>
</html>
