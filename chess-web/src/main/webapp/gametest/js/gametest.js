
var orientation;
var gameUrl = "http://127.0.0.1:8555/api/game";
var userId;
var gameId = null;
var board;
var game;

var init = function() {

//--- start example JS ---
    game = new Chess();


    var turnFunc = function(from, to) {
        var dataVal = {gameId: gameId, userId: userId, startPosition: from, endPosition: to, fen: game.fen(), gameOver: game.game_over()};
        var request = $.ajax({
            url: gameUrl + "/turn",
            method: "POST",
            data: JSON.stringify(dataVal),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            cache: false
        }).done(function (data) {
            if (console && console.log) {
                console.log("Sample of data:", data);
            }
        });
    };

    var onDragStart = function(source, piece, position, orientation) {
        // do not pick up pieces if the game is over
        // only pick up pieces for the side to move
        if (game.game_over() === true ||
            (game.turn() === 'w' && piece.search(/^b/) !== -1) ||
            (game.turn() === 'b' && piece.search(/^w/) !== -1)) {
            return false;
        }

        // only allow pieces to be dragged when the board is oriented
        // in their direction
        if ((orientation === 'white' && piece.search(/^w/) === -1) ||
            (orientation === 'black' && piece.search(/^b/) === -1)) {
            return false;
        }
    };

    var onDrop = function(source, target) {
        // see if the move is legal
        var move = game.move({
            from: source,
            to: target,
            promotion: 'q' // NOTE: always promote to a queen for example simplicity
        });

        // illegal move
        if (move === null) return 'snapback';

        turnFunc(source, target);

        updateStatus();
    };

// update the board position after the piece snap
// for castling, en passant, pawn promotion
    var onSnapEnd = function() {
        board.position(game.fen());
    };

    var cfg = {
        pieceTheme: 'images/chesspieces/alpha/{piece}.png',
        draggable: true,
        position: 'start',
        onDragStart: onDragStart,
        onDrop: onDrop,
        onSnapEnd: onSnapEnd
        , orientation: orientation
    };
    board = new ChessBoard('board', cfg);

    updateStatus();
};

$(document).ready(function(){

    var newGameFunc = function(userPlayWhite) {
        orientation = userPlayWhite ? "white" : "black";

        var dataVal = {isWhite: userPlayWhite, gameLength: 3600};
        var request = $.ajax({
            url: gameUrl + "/new-game",
            method: "POST",
            data: JSON.stringify(dataVal),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            cache: false
        }).done(function (data) {
            gameId = data.gameId;
            if (console && console.log) {
                console.log("Sample of data:", data);
            }
            init();
        });
    };

    var connectToGameFunc = function(curGameId) {
        var request = $.ajax({
            url: gameUrl + "/connect/" + curGameId,
            method: "POST",
            data: JSON.stringify({}),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            cache: false
        }).done(function (data) {
            gameId = data.gameId;
            orientation = orientation = data.isWhite ? "white" : "black";
            if (console && console.log) {
                console.log("Sample of data:", data);
            }
            init();

        });
    };

    var getGameFunc = function(curGameId) {
        if(curGameId == null){
            return;
        }

        var request = $.ajax({
            url: gameUrl + "/" + curGameId,
            method: "GET",
            data: JSON.stringify({}),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            cache: false
        }).done(function (data) {
            game.load(data.fen);
            board.position(game.fen());
            //gameId = data.gameId;
            if (console && console.log) {
                console.log("Sample of data:", data);
            }
            updateStatus();

        });
    };

    $("#newGame").click(function(){
        if($("#pieceColor").val() == "white") {
            newGameFunc(true);
        }else{
            newGameFunc(false);
        }
    });

    $("#connectToGame").click(function() {
        connectToGameFunc($("#gameIdInput").val());
    });

    $("#getGame").click(function() {
        getGameFunc(gameId);
    });

    function getGame_trigger(){
        getGameFunc(gameId);
    }

    setInterval(getGame_trigger, 2000);



})


var updateStatus = function() {
    var status = '';
    var statusEl = $('#status'),
        fenEl = $('#fen'),
        pgnEl = $('#pgn');

    var moveColor = 'White';
    if (game.turn() === 'b') {
        moveColor = 'Black';
    }

    // checkmate?
    if (game.in_checkmate() === true) {
        status = 'Game over, ' + moveColor + ' is in checkmate.';
    }

    // draw?
    else if (game.in_draw() === true) {
        status = 'Game over, drawn position';
    }

    // game still on
    else {
        status = moveColor + ' to move';

        // check?
        if (game.in_check() === true) {
            status += ', ' + moveColor + ' is in check';
        }
    }

    statusEl.html(status);
    fenEl.html(game.fen());
    pgnEl.html(game.pgn());
    $('#gameId').html(gameId);
};



