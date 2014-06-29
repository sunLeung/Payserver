<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
     <title>Learning AngularJS</title>
     <script src="scripts/angular.min.js" type="text/javascript"></script>
     <script src="scripts/app.js" type="text/javascript"></script>
     <script src="scripts/controllers/maincontroller.js" type="text/javascript"></script>
</head>
<body>
     <div id='content' ng-app='MyTutorialApp' ng-controller='MainController'>
		<input type='text' ng-model='inputValue' />
		{{inputValue}}
		<select ng-model='selectedPerson' ng-options='obj.name for obj in people'></select>
			<select ng-model='selectedGenre'>
				<option ng-repeat='label in people[selectedPerson.id].music'>{{label}}</option>
		</select>
	<div ng-controller='con'>
		{{fuck}}
	</div>
	</div>
</body>
</html>