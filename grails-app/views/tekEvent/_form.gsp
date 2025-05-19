<%@ page import="com.tekdays.TekEvent" %>



<div class="fieldcontain ${hasErrors(bean: tekEventInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="tekEvent.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${tekEventInstance?.name}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: tekEventInstance, field: 'city', 'error')} required">
	<label for="city">
		<g:message code="tekEvent.city.label" default="City" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="city" required="" value="${tekEventInstance?.city}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: tekEventInstance, field: 'description', 'error')} required">
	<label for="description">
		<g:message code="tekEvent.description.label" default="Description" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="description" cols="40" rows="5" maxlength="255"  value="${tekEventInstance?.description}"/>

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
	<g:textField name="organizerName" required="" value="${tekEventInstance?.organizer?.fullName}"/>

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
<div class="fieldcontain ${hasErrors(bean: tekEventInstance, field: 'volunteers', 'error')}">
	<label for="volunteers" %{--style="float: left; width: 200px; font-weight: bold;"--}%>
		<g:message code="tekEvent.venue.label" default="Volunteers" />
	</label>
	<div style="margin-left: 200px;">
		<g:each in="${com.tekdays.TekUser.list()}" var="volunteer">
			<div>
				<label>
					<g:checkBox name="volunteerIds"
								value="${volunteer.id}"
								checked="${tekEventInstance?.volunteers?.id?.contains(volunteer.id)}"/>
					${volunteer.fullName.encodeAsHTML()}
				</label>
			</div>
		</g:each>
	</div>
</div>




