var app = angular.module('app', []);

// Create an AngularJS service called debounce
app.factory('debounce', [ '$timeout', '$q', function($timeout, $q) {
	// The service is actually this function, which we call with the func
	// that should be debounced and how long to wait in between calls
	return function debounce(func, wait, immediate) {
		var timeout;
		// Create a deferred object that will be resolved when we need to
		// actually call the func
		var deferred = $q.defer();
		return function() {
			var context = this, args = arguments;
			var later = function() {
				timeout = null;
				if (!immediate) {
					deferred.resolve(func.apply(context, args));
					deferred = $q.defer();
				}
			};
			var callNow = immediate && !timeout;
			if (timeout) {
				$timeout.cancel(timeout);
			}
			timeout = $timeout(later, wait);
			if (callNow) {
				deferred.resolve(func.apply(context, args));
				deferred = $q.defer();
			}
			return deferred.promise;
		};
	};
} ]);

app.controller('ctrl', function($scope, $http, debounce) {
	$scope.data = {};

	$scope.save = function() {
	}

	$scope.insert = function() {
		$scope.data.item = ($scope.data.item || []);
		$scope.data.item.push({});
	}

	$scope.$watch('data', function() {
		if (!$scope.data || !$scope.data.workflow || !$scope.data.workflow.tarefa) {
			if ($scope.data && $scope.data.workflow)
				$scope.data.workflow.dot = undefined;
			document.getElementById('graph-workflow').innerHTML = "";
			$scope.tarefas = undefined;
			return;
		}
		var tarefas = [];

		for (var i = 0; i < $scope.data.workflow.tarefa.length; i++) {
			var t = $scope.data.workflow.tarefa[i];
			if (!t.id)
				t.id = uuidv4();
			t.nome = i + 1 + ") " + (t.titulo ? t.titulo : t.tipo);
			tarefas.push({
				id : t.id,
				nome : t.nome
			})
		}
		tarefas.push({
			id : "fim",
			nome : "[Fim]"
		})

		$scope.tarefas = tarefas;
		console.log('graphDrawDebounced')
		$scope.graphDrawDebounced();
	}, true);

	$scope.graphDraw = function() {
		console.log('graphDraw')
		$scope.dot = $scope.getDot($scope.data.workflow);
		$http({
			url : 'https://hml.xrp.com.br/app/graphviz?graph=' + $scope.dot,
			method : "GET"
		}).then(function(response) {
			document.getElementById('graph-workflow').innerHTML = response.data;
		}, function(response) {
			$scope.showError(response);
		});
	}

	$scope.graphDrawDebounced = debounce($scope.graphDraw, 1000, false);

	function graphElement(shape, n, nextn) {
		var s = '"' + n.id + '"[shape="' + shape + '"][label=<<font>' + n.titulo + '</font>>];';
		if (n.tipo !== "FIM") {
			if (n.desvio && n.desvio.length > 0) {
				for ( var x in n.desvio) {
					var tr = n.desvio[x];
					if (tr.tarefa)
						s += '"' + n.id + '"->"' + tr.tarefa + '"';
					else if (nextn)
						s += '"' + n.id + '"->"' + nextn.id + '"';
					else
						s += '"' + n.id + '"->"fim"';
					if (tr.titulo && tr.titulo !== '')
						s += ' [label="' + tr.titulo + '"]';
					s += ';';
				}
			} else if (n.depois) {
				s += '"' + n.id + '"->"' + n.depois + '";';
			} else if (nextn) {
				s += '"' + n.id + '"->"' + nextn.id + '";';
			} else {
				s += '"' + n.id + '"->"fim";';
			}
		}
		return s;
	}

	$scope.getDot = function(wf) {
		var tipos = {
			INCLUIR_DOCUMENTO : 'note',
			FORMULARIO : 'rectangle',
			DECISAO : 'diamond',
			EMAIL : 'folder',
		};
		var s = 'digraph G { graph[size="3,3"];';
		if (wf.tarefa.length > 0) {
			s += graphElement("oval", {
				id : "ini",
				titulo : "Início",
				desvio : [ {
					tarefa : wf.tarefa[0].id
				} ]
			});
			s += graphElement("oval", {
				id : "fim",
				titulo : "Fim",
				tipo : "FIM"
			});
			for (var i = 0; i < wf.tarefa.length; i++) {
				var n = wf.tarefa[i];
				s += graphElement(tipos[n.tipo], n, i < wf.tarefa.length - 1 ? wf.tarefa[i + 1] : undefined);
			}
		}
		s += '}';
		return s;
	}

});

var uuidv4 = function() {
	return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
		var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
		return v.toString(16);
	});
};
