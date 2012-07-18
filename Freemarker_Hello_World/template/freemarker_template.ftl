   <orders> 
<#list doc.order.orderitems.orderitem as orderitem>
  <order>
  <order-id>${doc.order.@id}</order-id>
  <qty>${orderitem.quantity}</qty>
  <price>${orderitem.price}</price>
  <product> ${orderitem.product} </product>
  </order>
</#list>
    </orders>
