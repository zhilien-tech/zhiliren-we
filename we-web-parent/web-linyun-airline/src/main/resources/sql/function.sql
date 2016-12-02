/*function_manage_list*/
SELECT
  t.id,
  t.parentId,
  t.level,
  p.name parentName ,
  t.name,
  t.url,
  t.remark,
  t.createTime,
  t.updateTime,
  t.sort
FROM t_function t LEFT JOIN t_function p
ON t.parentId=p.id
$condition

/*function_manage_list_count*/
SELECT COUNT(*) 
FROM t_function t LEFT JOIN t_function p
ON t.parentId=p.id
$condition