<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="Grails"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${assetPath(src: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${assetPath(src: 'apple-touch-icon-retina.png')}">
  		<asset:stylesheet src="application.css"/>
		<asset:javascript src="application.js"/>
		<g:layoutHead/>
		<%
			if (!session.lang) {
				session.lang = new Locale("en")
			}
		%>


</head>
	<body style="margin: auto !important;">
%{--
		<div id="grailsLogo" role="banner"><a href="http://grails.org"><asset:image src="grails_logo.png" alt="Grails"/></a></div>
--}%
<g:set var="currentLang" value="${session.lang?.language}" />

	<div id="logo" role="banner"> <a href = "${createLink(uri: '/', params: currentLang ? [lang: currentLang] : [:])}"/>
		<img src="${resource(dir: 'images', file: 'td_logo.png')}"
			 alt="TekDays"
			 style="width: 960px; height: auto;"/> </a> </div>
	<g:if test="${session.user && !session.user.activated && !(controllerName == 'tekUser' && actionName == 'activationPage')}">
		<g:activationButton />
	</g:if>
	<div class="language-switcher">
		<a href="${request.forwardURI}?lang=en">English</a> |
		<a href="${request.forwardURI}?lang=ru">Русский</a> |
		<a href="${request.forwardURI}?lang=hy">Հայերեն</a>
	</div>

		<g:layoutBody/>
		<g:loginToggle />
	    <g:registrationLink />

	<div class="footer" role="contentinfo"></div>
%{--		<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>--}%
	</body>
</html>
