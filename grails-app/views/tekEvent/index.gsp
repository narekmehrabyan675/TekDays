
<%@ page import="com.tekdays.TekEvent" %>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'tekEvent.label', default: 'TekEvent')}" />


		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-tekEvent" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-tekEvent" class="content scaffold-list" role="main">
			<h1 style="margin:  0 !important"><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'tekEvent.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="city" title="${message(code: 'tekEvent.city.label', default: 'City')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'tekEvent.description.label', default: 'Description')}" />
					
%{--
						<g:sortableColumn property="endDate" title="${message(code: 'tekEvent.endDate.label', default: 'End Date')}" />
--}%

%{--
						<th><g:message code="tekEvent.organizer.label" default="Organizer" /></th>
--}%
						<g:sortableColumn property="venue" title="${message(code: 'tekEvent.venue.label', default: 'Venue')}" />


						<g:sortableColumn property="startDate" title="${message(code: 'tekEvent.startDate.label', default: 'Start Date')}" />

						<g:sortableColumn property="Revision" title="${message(code: 'tekEvent.startDate.label', default: 'Revision')}" />


					</tr>
				</thead>
				<tbody>
				<g:each in="${tekEventInstanceList}" status="i" var="tekEventInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${tekEventInstance.id}">${fieldValue(bean: tekEventInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: tekEventInstance, field: "city")}</td>

						<td>${fieldValue(bean: tekEventInstance, field: "description")}</td>

						<td>${fieldValue(bean: tekEventInstance, field: "venue")}</td>


						%{--
                                                <td><g:formatDate date="${tekEventInstance.endDate}" /></td>
                        --}%

%{--
						<td>${fieldValue(bean: tekEventInstance, field: "organizer")}</td>
--}%

						<td><g:formatDate date="${tekEventInstance.startDate}" /></td>

						%{--<td><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal" data-id = "${tekEventInstance.id}">
							Revision list
						</button> </td>--}%

						<td>
							<button
									type="button"
									class="btn btn-primary openModalBtn"
						data-toggle="modal"
						data-target="#myModal"
						data-id="${tekEventInstance.id}">
						Revision list
						</button>
						</td>


					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${tekEventInstanceCount ?: 0}" />
			</div>

			<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
				<div class="modal-dialog custom-size" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title">Search HQL</h5>
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>

						<div class="modal-body" style="height: 600px; padding: 0;">
							<iframe id="modalIframe" src="" style="width: 100%; height: 100%; border: none;"></iframe>
						</div>
					</div>
				</div>
			</div>
			<style>
			.custom-size {
				max-width: 95%;
				width: 1000px;
				height: 800px
			}

			.modal-body {
				height: 800px !important;
				padding: 0;
			}
			</style>



		</div>


	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

	<script>
		var currentId = null;

		$('.openModalBtn').on('click', function () {
			currentId = $(this).data('id'); // saving  id

			$('#myModal').modal({
				backdrop: false,
				keyboard: true
			});
		});

		$('#myModal').on('show.bs.modal', function () {
			if (currentId !== null) {
				const host = window.location.hostname;
				//$('#modalIframe').attr('src', 'http://' + host + ':9090/TekDays/searchable/updatedHQL');
				$('#modalIframe').attr('src', 'http://' + host + ':9090/TekDays/tekEvent/revisions/' + currentId);
			}
		});

		$('#myModal').on('hidden.bs.modal', function () {
			$('#modalIframe').attr('src', '');
			currentId = null;
		});
	</script>




	</body>
</html>
