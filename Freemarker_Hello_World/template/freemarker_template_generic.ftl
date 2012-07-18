<?xml version="1.0"encoding="UTF-8"?>

<#list doc.order?children as c>
- ${c?node_type} <#if c?node_type = 'element'>${c?node_name}</#if>
</#list> 

		
	
<#list doc.order.@@ as attr>
- ${attr?node_name} = ${attr}
</#list> 


		
	
<#list doc.order.* as c>
- ${c?node_name}
</#list>  



   <salesorder>
    <details>
        <orderid>${doc["order/@id"]}</orderid>
        <customer>
           
            <name>${doc["order/header/customer"]}</name>
        </customer>
    </details>
   <#list doc["order/order-items"]  as order_items>
   <itemList>
    
   		<#list doc["order/order-items/order-item"]  as order_item>
   		<item>
   		 <orderid>${doc["order/header/customer/@number"]}</orderid>
   			<id>${order_item.@id}</id>
   			<productid>${order_item.product}</productid>
   			<quantity>${order_item.quantity[0]!""}</quantity>
   			<price>${order_item.price[0]!""}</price>
   			
   			 	 <#list doc["order/order-items/order-item/quantity"]  as prd>
   	 ${prd}
   	 ${order_item.priceprice[0]!"0"} * ${prd} 
   	 </#list>
   	 
   		</item>
   		</#list>
   	</itemList>
   	</#list>   
  
   
</salesorder>