ALTER TABLE `delivery`.`usuario` 
ADD COLUMN `datacriacao` DATETIME NULL AFTER `userperfil`,
ADD COLUMN `dataatualizacao` DATETIME NULL AFTER `datacriacao`;