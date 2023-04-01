<template>
	<view class="page" @touchstart="clickDown" @touchend="clickUp">
		<image :src="imageURL" class="img"></image>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				manager:null,
				imageURL:"/static/bg.JPG"
			}
		},
		onLoad() {
			let that = this;
			this.manager = uni.getRecorderManager();
			this.manager.onStop(function(result){
				console.log("录音结束")
				that.imageURL = "/static/bg.JPG";
				let filePath = result.tempFilePath
				const option = {
				        url: 'http://192.168.43.36:8080/login',
						filePath,
				        formData: {
				            filePath
				        },
				        name: 'file',
						success:function(result){
							let data = JSON.parse(result.data);
							console.log(data)
							if(data.code == 1){
								uni.showToast({
									title:"服务错误",
									icon:"error"
								})
							}
							
							uni.showToast({
								title:data.data.identify,
								icon:"none"
							})
						}
				    }
				    uni.uploadFile(option)

			});
		},
		methods: {
			//按下
			clickDown(){
				this.imageURL = "/static/bg.gif";
				this.manager.start({
					duration:3500,
					sampleRate:16000,
					numberOfChannels:1,
					encodeBitRate:24000,
					format:"wav"
				});
			},
			//提起
			clickUp(){
				console.log("提起")
				this.imageURL = "/static/bg.JPG";
				this.manager.pause();
				this.manager.stop();
			}
		}
	}
</script>

<style scoped lang="scss">
	.page {
		height: 100%;
		background-repeat: no-repeat;
		background-color: #000;
		
		.img{
			width: 100vw;
			margin-top: 30vh;
		}
	}
</style>
