'use strict';

app
.directive('plumeMediaGallery', function() {
	return {
		restrict: 'E',
		scope: {
			attachedData: '=', // data object to be attached to the gallery context
			insertMedia: '=', // function called when a media is attached ; returns a promise to a null object and use parameters : mediaUpload, attachedData
			deleteMedia: '=', // function called when a media is deleted ; returns a promise to a null object and use parameters : mediaFile, attachedData
			loadMedias: '=', // function called to load medias ; returns a promise to a list of mediaFile and use parameters : attachedData
			accept: '@' // by default: *
		},
		templateUrl: 'app/directives/media-gallery/plm-media-gallery.html',
		controller: function($scope, $translate, uiService) {
			var loadMediasInternal = function(loadMedias, attachedData) {
				loadMedias(attachedData)
					.then(function(medias) {
						$scope.medias = medias;
					});
			};
			
			$scope.medias = [];
			
			$scope.isImage = $scope.accept && $scope.accept.startsWith('image');
			
			$scope.$watch('attachedData', function(attachedData) {
				loadMediasInternal($scope.loadMedias, attachedData);
			});
			
			$scope.internalDeleteMedia = function(media) {
				uiService
					.withConfirmation($translate.instant('file.DELETE_MEDIA'))
					.then(function() {
						uiService
							.withPromise($scope.deleteMedia(media.idFile, $scope.attachedData), true)
							.then(function() {
								loadMediasInternal($scope.loadMedias, $scope.attachedData);
							})
							.catch(console.log);
					})
					.catch(console.log);
			};
			
			$scope.mediaLoaded = function(event, fileBase64) {
				if(fileBase64.length > 0) {
					uiService
						.withPromise(
							$scope
								.insertMedia(
									{
										data: fileBase64[0],
										initialPosition: $scope.medias.length
									},
									$scope.attachedData
								),
							true
						)
						.then(function() {
							loadMediasInternal($scope.loadMedias, $scope.attachedData);
						});
				}
			};
		}
	};
});

