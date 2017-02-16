<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="sf" uri="http://www.springframework.org/tags/form" %>

<c:url value='/exams/save' context='/College' var="action"/>
<div class="row">
<h3 class="text-center">Add New Exam</h3><br>
<sf:form action="${action}" method="post" class="form-horizontal" modelAttribute="exam">

<sf:hidden path="id"/>

<div class="form-group">
<sf:label  path="date" class="control-label col-sm-3 col-sm-offset-2">DATE</sf:label>
<div class="col-sm-4">
<div class="input-group" >
<sf:input path="date" class="form-control datepicker" readonly="true"/>
<span class="input-group-addon showDate">
<span class="glyphicon glyphicon-calendar"></span>
</span>
</div>
<sf:errors class="text-danger" path="date" />
</div>
</div>

<div class="form-group">
<sf:label  path="subject" class="control-label col-sm-3 col-sm-offset-2">SUBJECT</sf:label>
<div class="col-sm-4">
<sf:select path="subject" class="form-control">
<sf:option value=""></sf:option>
<sf:options items="${studyProgram.subjects}" itemLabel="title" itemValue="id"/>
</sf:select>
<sf:errors class="text-danger" path="subject" />
</div>
</div>

<div class="form-group">
<sf:label  path="professor" class="control-label col-sm-3 col-sm-offset-2">PROFESSOR</sf:label>
<div class="col-sm-4">
<sf:select path="professor" class="form-control">
<sf:option value=""></sf:option>
<sf:options items="${studyProgram.professors}" itemLabel="fullName" itemValue="id"/>
</sf:select>
<sf:errors class="text-danger" path="professor" />
</div>
</div> 

<div class="form-group">
<sf:label  path="student" class="control-label col-sm-3 col-sm-offset-2">STUDENT</sf:label>
<div class="col-sm-4">
<sf:select path="student" class="form-control">
<sf:option value=""></sf:option>
<sf:options items="${studyProgram.students}" itemLabel="fullName" itemValue="id"/>
</sf:select>
<sf:errors class="text-danger" path="student" />
</div>
</div>



<div class="form-group">
<sf:label  path="score" class="control-label col-sm-3 col-sm-offset-2">SCORE</sf:label>
<div class="col-sm-4">
<sf:input path="score" class="form-control" />
<sf:errors class="text-danger" path="score" />
</div>
</div>

<div class="form-group">
    <div class="col-sm-offset-5 col-sm-4">
     <div class="btn-group">
      <button type="submit" class="btn btn-primary">Save</button>
      <button type="reset" class="btn btn-default">Reset</button>
      </div>
    </div>
  </div>

</sf:form>
</div>