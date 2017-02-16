<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="row">
<div class="col-sm-12">

<c:url value='/password/save' context='/College' var="action"/>
<div class="row" ng-app="myApp" ng-controller="myCtrl">

<h3 class="text-center">Change Password</h3><br>
<h4 class="text-center">${message}</h4>
<sf:form method="post" action="${action}" class="form-horizontal" commandName="user">

<div class="form-group hidden">
<div class="col-sm-4">
<sf:hidden path="username"/>
<sf:hidden path="email"/>
<sf:select path="roles"  class="form-control" multiple="multiple">
<sf:options items="${listRoles}" itemLabel="role" itemValue="id"/>
</sf:select>
</div>
</div>

<div class="form-group">
<sf:label path="password" class="control-label col-sm-3 col-sm-offset-2" >PASSWORD</sf:label>
<div class="col-sm-4">
<sf:password  class="form-control" path="password" ng-model="password" ng-keyup="checkPasswordMatch()"/>
<span ng-show="notCorrectPassword" class="text-danger">
Password must be between 8 and 15 characters long.
</span>
</div>
</div>

<div class="form-group">
<label for="email" class="control-label col-sm-3 col-sm-offset-2" >CONFIRM PASSWORD</label>
<div class="col-sm-4" >
<input type="password" class="form-control" ng-model="password1" ng-keyup="checkPasswordMatch()"/>

<span class="text-danger" ng-show="notMatch ">
Password do not match. <br>
</span>

<span class="text-success" ng-show="match">
Password match. <br>
</span>

</div>
</div>

<div class="form-group">
    <div class="col-sm-offset-5 col-sm-4">
     <div class="btn-group">
      <button class="btn btn-primary" ng-disabled="!canSubmit">Save</button>
      <button type="reset" class="btn btn-default">Reset</button>
      </div>
    </div>
  </div>
  
</sf:form>

</div>

</div>
</div>