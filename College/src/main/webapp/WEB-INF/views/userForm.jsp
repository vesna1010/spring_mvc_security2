<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="row">
<div class="col-sm-12">

<c:url value='/users/save' context='/College' var="action"/>

<div class="row" ng-app="myApp" ng-controller="myCtrl">
<h3 class="text-center">Add New User</h3><br>
<h4 class="text-center text-success">${message}</h4>
<sf:form method="post" name="userForm" action="${action}" class="form-horizontal"
commandName="user">

<div class="form-group">
<sf:label path="username" class="control-label col-sm-3 col-sm-offset-2">USERNAME</sf:label>
<div class="col-sm-4">
<sf:input ng-model="username" class="form-control" path="username" ng-required="true" 
ng-pattern="/^([a-zA-Z0-9]+\s?){8,15}$/"/>
<div class="text-danger">
<span ng-show="userForm.username.$dirty && userForm.username.$error.required">
Enter valid username. <br>
</span>
<span ng-show="userForm.username.$dirty && userForm.username.$error.pattern">
Username must be between 8 and 15 characters long (only alphabetic characters and numbers).
</span>
</div>
</div>
</div>

<div class="form-group">
<sf:label path="email" class="control-label col-sm-3 col-sm-offset-2">EMAIL</sf:label>
<div class="col-sm-4">
<sf:input ng-model="email" class="form-control" path="email" ng-required="true"
ng-pattern="/^[a-zA-Z0-9_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/"/>
<div class="text-danger">
<span ng-show="userForm.email.$dirty && userForm.email.$error.required">
Enter email. <br>
</span>
<span ng-show="userForm.email.$dirty && userForm.email.$error.pattern">
Enter valid email.
</span>
</div>
</div>
</div>

<div class="form-group">
<sf:label path="password" class="control-label col-sm-3 col-sm-offset-2">PASSWORD</sf:label>
<div class="col-sm-4">
<sf:password ng-model="password" class="form-control" path="password" 
ng-required="true" ng-pattern="/^(\S){8,15}$/"/>
<div class="text-danger">
<span ng-show="userForm.password.$dirty && userForm.password.$error.required">
Enter password. <br>
</span>
<span ng-show="userForm.password.$dirty && userForm.password.$error.pattern">
Password must be between 8 and 15 characters long.
</span>
</div>
</div>
</div>

<div class="form-group">
<label for="roles" class="control-label col-sm-3 col-sm-offset-2">ROLES</label>
<div class="col-sm-4">
<sf:select ng-model="roles" path="roles" ng-required="true" 
class="form-control" multiple="multiple">
<sf:options items="${listRoles}" itemLabel="role" itemValue="id"/>
</sf:select>
<div class="text-danger">
<span ng-show="userForm.roles.$error.required && !userForm.password.$error.pattern
&& !userForm.username.$error.pattern && !userForm.email.$error.pattern
&& !userForm.password.$error.required && !userForm.username.$error.required && !userForm.email.$error.required">
Select roles.
</span>
</div>
</div>
</div>

<div class="form-group">
    <div class="col-sm-offset-5 col-sm-4">
     <div class="btn-group">
      <button class="btn btn-primary" ng-disabled="userForm.$invalid">Save</button>
      <button type="reset" class="btn btn-default">Reset</button>
      </div>
    </div>
  </div>
</sf:form>
</div>


</div>
</div>




