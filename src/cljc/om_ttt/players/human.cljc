(ns om-ttt.players.human
  (:require [om-ttt.game.board :as b]
            [om-ttt.protocols.player :refer [Player]]
            [om-ttt.protocols.ui :as ui]))

(deftype HumanPlayer [token ui]
  Player
  (make-move [this board]
    (b/fill-space board (ui/move ui board) token)))

(defn new-human-player [player-token ui]
  (HumanPlayer. player-token ui))