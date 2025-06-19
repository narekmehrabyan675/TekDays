<%@ page import="com.tekdays.TekEvent" %>
<style>
ul.errors {
    width: 300px;
    font-size: 12px;
    padding: 4px 8px;
    background-color: #fee;
    border: 1px solid #f88;
    border-radius: 4px;
}

ul.errors li {
    margin: 0;
    padding: 2px 0;
}
</style>


<div class="fieldcontain ${hasErrors(bean: tekEventInstance, field: 'name', 'error')}">
    <label for="name">
        <g:message code="tekEvent.name.label" default="Name" />
    </label>

    <g:textField name="name" value="${tekEventInstance?.name}" />

    <g:hasErrors bean="${tekEventInstance}" field="name">
        <ul class="errors"
            style="margin-top: 4px; margin-bottom: 0; font-size: 12px; background: #fee; border: 1px solid #f88; border-radius: 4px; padding: 4px 8px; display: inline-block;">
            <g:eachError bean="${tekEventInstance}" field="name" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                    <g:message error="${error}" />
                </li>
            </g:eachError>
        </ul>
    </g:hasErrors>
</div>



%{--<div class="fieldcontain ${hasErrors(bean: tekEventInstance, field: 'name', 'error')} --}%%{--required--}%%{--">
    <label for="name">
        <g:message code="tekEvent.name.label" default="Name" />
      --}%%{--  <span class="required-indicator">*</span>--}%%{--
    </label>
    <g:textField name="name"  value="${tekEventInstance?.name}"/>
    <div>
    <g:hasErrors bean="${tekEventInstance}" field="name">
        <ul class="errors" role="alert">
            <g:eachError bean="${tekEventInstance}" field="name" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    </div>

</div>--}%
<div class="fieldcontain ${hasErrors(bean: tekEventInstance, field: 'name', 'error')}">
    <label for="city">
        <g:message code="tekEvent.name.label" default="City" />
    </label>

    <g:textField name="city" value="${tekEventInstance?.city}" />

    <g:hasErrors bean="${tekEventInstance}" field="city">
        <ul class="errors"
            style="margin-top: 4px; margin-bottom: 0; font-size: 12px; background: #fee; border: 1px solid #f88; border-radius: 4px; padding: 4px 8px; display: inline-block;">
            <g:eachError bean="${tekEventInstance}" field="city" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                    <g:message error="${error}" />
                </li>
            </g:eachError>
        </ul>
    </g:hasErrors>
</div>


<div class="fieldcontain ${hasErrors(bean: tekEventInstance, field: 'description', 'error')} ">
    <label for="description">
        <g:message code="tekEvent.description.label" default="Description" />

    </label>
    <g:textArea name="description" cols="40" rows="5" maxlength="500" value="${tekEventInstance?.description}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: tekEventInstance, field: 'respondents', 'error')} ">
    <label for="respondents">
        <g:message code="tekEvent.respondents.label" default="Respondents" />

    </label>


</div>

<div class="fieldcontain ${hasErrors(bean: tekEventInstance, field: 'volunteers', 'error')} ">
    <label for="volunteers">
        <g:message code="tekEvent.volunteers.label" default="Volunteers" />

    </label>
    <g:select name="volunteers" from="${com.tekdays.TekUser.list()}" multiple="multiple" optionKey="id" size="5" value="${tekEventInstance?.volunteers*.id}" class="many-to-many"/>

</div>

%{--<div class="fieldcontain ${hasErrors(bean: tekEventInstance, field: 'sponsorships', 'error')} ">
    <label for="sponsorships">
        <g:message code="tekEvent.sponsorships.label" default="Sponsorships" />

    </label>

  --}%%{--  <ul class="one-to-many">
        <g:each in="${tekEventInstance?.sponsorships?}" var="s">
            <li><g:link controller="sponsorship" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
        </g:each>
        <li class="add">
            <g:link controller="sponsorship" action="create" params="['tekEvent.id': tekEventInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'sponsorship.label', default: 'Sponsorship')])}</g:link>
        </li>
    </ul>--}%%{--



</div>--}%
<div class="fieldcontain">
    <label for="sponsorId">Sponsor</label>
    <g:select name="sponsorId" from="${com.tekdays.Sponsor.list()}" optionKey="id"/>
</div>


<div class="fieldcontain ${hasErrors(bean: tekEventInstance, field: 'tasks', 'error')} ">
    <label for="tasks">
        <g:message code="tekEvent.tasks.label" default="Tasks" />

    </label>


    <ul class="one-to-many">
        <g:each in="${tekEventInstance?.tasks?}" var="t">
            <li><g:link controller="task" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></li>
        </g:each>
        <li class="add">
            <g:link controller="task" action="create" params="['tekEvent.id': tekEventInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'task.label', default: 'Task')])}</g:link>
        </li>
    </ul>


</div>



<div class="fieldcontain ${hasErrors(bean: tekEventInstance, field: 'messages', 'error')} ">
    <label for="messages">
        <g:message code="tekEvent.messages.label" default="Messages" />

    </label>

    <ul class="one-to-many">
        <g:each in="${tekEventInstance?.messages?}" var="m">
            <li><g:link controller="tekMessage" action="show" id="${m.id}">${m?.encodeAsHTML()}</g:link></li>
        </g:each>
        <li class="add">
            <g:link controller="tekMessage" action="create"
                    params="['tekEvent.id': tekEventInstance?.id]">
                ${message(code: 'default.add.label', args: [message(code: 'tekMessage.label', default: 'TekMessage')])}</g:link>
        </li>
    </ul>


</div>

<div class="fieldcontain ${hasErrors(bean: tekEventInstance, field: 'endDate', 'error')} required">
    <label for="endDate">
        <g:message code="tekEvent.endDate.label" default="End Date" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="endDate" precision="day"  value="${tekEventInstance?.endDate}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: tekEventInstance, field: 'organizer', 'error')} required">
    <label for="organizer">
        <g:message code="tekEvent.organizer.label" default="Organizer" />
        <span class="required-indicator">*</span>
    </label>
    <g:select id="organizer" name="organizer.id" from="${com.tekdays.TekUser.list()}" optionKey="id" required="" value="${tekEventInstance?.organizer?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: tekEventInstance, field: 'startDate', 'error')} required">
    <label for="startDate">
        <g:message code="tekEvent.startDate.label" default="Start Date" />
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="startDate" precision="day"  value="${tekEventInstance?.startDate}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: tekEventInstance, field: 'venue', 'error')} required">
    <label for="venue">
        <g:message code="tekEvent.venue.label" default="Venue" />
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="venue" required="" value="${tekEventInstance?.venue}"/>

</div>

