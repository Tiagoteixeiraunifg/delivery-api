ALTER TABLE `delivery`.`cliente` 
ADD COLUMN `datacriacao` DATETIME NULL AFTER `end_cep`,
ADD COLUMN `dataatualizacao` DATETIME NULL AFTER `datacriacao`;