/*grab_file_list*/
SELECT
	f.id,
	f.mailId,
	f.parentId,
	f.folderName,
	f.fileName,
	f.url,
	f.fileSize,
	f.type,
	f.`status`,
	f.createTime,
	f.updateTime,
	f.naval,
	f.fullPath,
	f.sort
FROM
	t_grab_file f
WHERE
	f.parentId =@pid
ORDER BY f.sort LIMIT 0,1