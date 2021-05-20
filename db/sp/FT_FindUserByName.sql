DELIMITER $$

CREATE PROCEDURE FT_FindUserByName(
    IN p_name VARCHAR(50)
)
BEGIN
    SELECT name, password FROM FT_USERS WHERE name = p_name;
END$$
DELIMITER ;
