<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="sf" uri="http://www.springframework.org/tags/form" %>

<div class="row">
<div class="col-sm-12">

<c:if test="${studyProgram==null }"><h2 class="text-center text-danger">Not Found</h2></c:if>

<c:if test="${studyProgram!=null }">
<c:url value='/studyPrograms/save' context='/College' var="action"/>

<div class="row">
<h3 class="text-center">${message}&nbsp;Study Program</h3><br>
<sf:form action="${action}" method="post" class="form-horizontal" modelAttribute="studyProgram">

<div class="form-group">
<sf:label path="id" class="control-label col-sm-3 col-sm-offset-2">ID</sf:label>
<div class="col-sm-4">
<c:if test="${message=='Update'}">
<sf:hidden path="id"/>
</c:if>
<sf:input path="id" class="form-control" disabled="${message=='Update'}"/>
<sf:errors class="text-danger" path="id" />
</div>
</div>

<div class="form-group">
<sf:label path="title" class="control-label col-sm-3 col-sm-offset-2">TITLE:</sf:label>
<div class="col-sm-4">
<sf:input path="title" class="form-control"/>
<sf:errors class="text-danger" path="title" />
</div>
</div>

<div class="form-group">
<sf:label path="dateOfCreation" class="control-label col-sm-3 col-sm-offset-2">
DATE OF CREATION</sf:label>
<div class="col-sm-4">
<div class="input-group">
<sf:input path="dateOfCreation" class="form-control datepicker" readonly="true"/>
<span class="input-group-addon showDate">
<span class="glyphicon glyphicon-calendar"></span>
</span>
</div>
<sf:errors class="text-danger" path="dateOfCreation" />
</div>
</div>

<div class="form-group">
<sf:label path="durationOfStudy" class="control-label col-sm-3 col-sm-offset-2">DURATION OF STUDY:</sf:label>
<div class="col-sm-4">
<sf:input path="durationOfStudy" class="form-control"/>
<sf:errors class="text-danger" path="durationOfStudy" />
</div>
</div>

<div class="form-group">
<sf:label path="department" class="control-label col-sm-3 col-sm-offset-2">
DEPARTMENT</sf:label>
<div class="col-sm-4">
<sf:select path="department" class="form-control">
<sf:option value=""></sf:option>
<sf:options items="${departments}" itemValue="id" itemLabel="title"/>
</sf:select>
<sf:errors class="text-danger" path="department" />
</div>
</div>

<div class="form-group">
    <div class="col-sm-offset-5 col-sm-4">
     <div class="btn-group">
       <button type="submit" name="button" value="save" class="btn btn-primary">Save</button>
      <button type="submit" name="button" value="reset" class="btn btn-default">Reset</button>
      </div>
    </div>
  </div>

</sf:form>
</div>
</c:if>

</div>
</div>