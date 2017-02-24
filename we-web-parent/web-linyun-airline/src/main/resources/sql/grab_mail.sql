/*grab_mail_max_sort*/
SELECT
	IFNULL(f.sort,0) sort
FROM
	t_grab_file f
WHERE
	f.parentId =@pid
ORDER BY f.sort LIMIT 0,1

/*grab_mail_file_level*/
SELECT
	COUNT(*)
FROM
	t_grab_mail m
WHERE
	m.theme =@theme
AND m.sender =@sender
AND m.addressee =@addressee
AND m.sendTime =@sendTime

/*grab_mail_url*/
SELECT
	f.url
FROM
	t_grab_file f
$condition