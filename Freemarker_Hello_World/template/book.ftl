<test>
	
<h1>${doc.book.title}</h1>


<h2>${doc.book.chapter[0].title}</h2>
<h2>${doc.book.chapter[1].title}</h2> 


		
	
<#list doc.book.chapter as ch>
  <h2>${ch.title}</h2>
</#list>   

</test>