USE magasin;
-- Base de données: `magasin`
-- Structure de la table `commande`
--
CREATE TABLE IF NOT EXISTS `commande` (
`id` tinyint(4) NOT NULL AUTO_INCREMENT, `nomarticle` varchar(20) NOT NULL DEFAULT '', `client` tinyint(4) NOT NULL DEFAULT '0', PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
-- Structure de la table `personnel`
CREATE TABLE IF NOT EXISTS `client` (
`id` tinyint(4) NOT NULL AUTO_INCREMENT, `nom` varchar(20) NOT NULL DEFAULT '', PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;