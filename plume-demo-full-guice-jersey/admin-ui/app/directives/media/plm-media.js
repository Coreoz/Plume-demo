'use strict';

app
.directive('plumeMedia', function() {
	return {
		restrict: 'E',
		scope: {
			mediaModel: '=model',
			currentMediaUrl: '=currentMediaUrl',
			accept: '@accept' // by default: *
		},
		templateUrl: 'app/directives/media/plm-media.html',
		controller: function($scope) {
			var basePreviewHref = null;
			
			var extractFilename = function(url) {
				if(!url) {
					return null;
				}
				
				var indexLastSlash = url.lastIndexOf("/");
				if(indexLastSlash == -1) {
					return null;
				}
				
				return url.substr(indexLastSlash + 1);
			};
			
			$scope.isImage = $scope.accept && $scope.accept.startsWith('image');
			
			$scope.$watch('currentMediaUrl', function(value) {
				basePreviewHref = value;
				$scope.previewHref = basePreviewHref;
				
				if(!$scope.isImage) {
					var fileName = extractFilename(value);
					// show the filename only if the file has an extension
					if(fileName != null && fileName.indexOf('.') > 0) {
						$scope.filename = fileName;
					}
				}
			});
			
			$scope.$watch('mediaModel', function(value) {
				if(value && value.base64) {
					if($scope.isImage) {
						$scope.previewHref = 'data:image/png;base64,' + value.base64;
					} else {
						$scope.filename = value.filename;
					}
				}
				else {
					$scope.previewHref = basePreviewHref;
				}
			});
		}
	};
});
