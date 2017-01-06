/*team_ticket_list*/
SELECT
	tt.*, ifnull(
		tu.ordersnum,
		'无订单号'
	) dingdanhao
FROM
	(
		SELECT
			tf.*, tfi.leavetime bleavetime,
			tfi.backtime bbacktime
		FROM
			(
				SELECT
					t.*, f.leavetime lleavetime,
					f.backtime lbacktime
				FROM
					t_plan_info t
				INNER JOIN t_flight_info f
				WHERE
					t.leaveairline = f.airlinenum
			) tf
		INNER JOIN t_flight_info tfi ON tf.backairline = tfi.airlinenum
	) tt
LEFT JOIN t_up_order_ticket tuo ON tt.id = tuo.ticketid
LEFT JOIN t_up_order tu ON tuo.orderid = tu.id
$condition
