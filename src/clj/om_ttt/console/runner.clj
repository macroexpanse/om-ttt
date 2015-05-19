(ns om-ttt.console.runner
  (:require [om-ttt.game-loop :as game-loop]
            [om-ttt.console.menu :as menu]
            [om-ttt.console.messages :as m]
            [om-ttt.players.player-factory :as player-factory]
            [om-ttt.board :as b]
            [om-ttt.protocols.ui :as ui]))

(declare get-options)
(declare create-board)

(defn- start-game [board config players ui]
  (game-loop/start-game board players)
  (if (ui/input-prompt ui m/play-again-prompt)
    (if (ui/input-prompt ui m/same-options-prompt)
      (recur (create-board (config :board-size)) config players ui)
      (get-options ui))))

(defn- create-players [config ui]
  (player-factory/create-players
    (config :ai-token) (config :human-token) (config :first-player) ui))

(defn- create-board [height]
  (b/generate height))

(defn- get-options [ui]
  (let [config (menu/get-game-config ui)
        players (create-players config ui)
        board (create-board (config :board-size))]
    (start-game board config players ui)))

(defn run [ui]
  (ui/display-message ui m/welcome-message)
  (get-options ui)
  (ui/display-message ui m/farewell-message))