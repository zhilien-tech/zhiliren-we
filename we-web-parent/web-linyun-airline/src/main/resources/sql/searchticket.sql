/*team_ticket_list*/
SELECT
	uo.ordersnum,
	pi.leavescity,
	pi.backscity,
	pi.leavesdate,
	pi.backsdate,
	pi.airlinename,
	pi.price,
	uo.amount,
	uo.orderstime,
	pi.opid
FROM
	(
		t_up_order_ticket uot
		INNER JOIN t_up_order uo ON uot.orderid = uo.id
	)
INNER JOIN t_plan_info pi ON uot.ticketid = pi.id
$condition
