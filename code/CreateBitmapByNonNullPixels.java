                                                int oldHeight = bm.getHeight();
						int oldWidth = bm.getWidth();
						// 其中getPixels中第三个参数要为图片的宽度
						int[] pixels = new int[oldWidth * oldHeight];// 保存所有的像素的数组，图片宽×高
						bm.getPixels(pixels, 0, oldWidth, 0, 0, oldWidth,
								oldHeight);
						// 转化为二维数组,方便计算.
						int pixels2d[][] = new int[oldHeight][oldWidth];
						for (int i = 0; i < oldHeight; i++) {
							for (int j = 0; j < oldWidth; j++) {
								pixels2d[i][j] = pixels[i * oldWidth + j];
							}
						}
						int minWidthPixel = oldWidth;// 有像素的最小的x轴的像素值.
						int maxWidthPixel = 0;// 有像素的最大的x轴的像素值.
						int minHeightPixel = oldHeight;// 有像素的最小的y轴的像素值.
						int maxHeightPixel = 0;// 有像素的最大的轴的像素值.
						for (int i = 0; i < oldHeight; i++) {
							for (int j = 0; j < oldWidth; j++) {
								int clr = pixels2d[i][j];
								int red = (clr & 0x00ff0000) >> 16; // 取高两位
								int green = (clr & 0x0000ff00) >> 8; // 取中两位
								int blue = clr & 0x000000ff; // 取低两位
								if (red != 255 && green != 255 && blue != 255) {
									// 非空白.
									if (minHeightPixel == oldHeight) {
										minHeightPixel = Math.min(
												minHeightPixel, i);// 获取最小的y轴的有像素值的像素.
									}
									minWidthPixel = Math.min(minWidthPixel, j);// 获取最小的x轴的有像素值的像素.
									break;
								}
							}
						}
						for (int i = oldHeight - 1; i >= 0; i--) {
							for (int j = oldWidth - 1; j >= 0; j--) {
								int clr = pixels2d[i][j];
								int red = (clr & 0x00ff0000) >> 16; // 取高两位
								int green = (clr & 0x0000ff00) >> 8; // 取中两位
								int blue = clr & 0x000000ff; // 取低两位
								if (red != 255 && green != 255 && blue != 255) {
									// 非空白.
									if (maxHeightPixel == 0) {
										maxHeightPixel = Math.max(
												maxHeightPixel, i);// 获取最大的y轴的有像素值的像素.
									}
									maxWidthPixel = Math.max(maxWidthPixel, j);// 获取最大的x轴的有像素值的像素.
									break;
								}
							}
						}
						// 宽度和高度方向上的最大的一边
						int max = Math.max(maxWidthPixel - minWidthPixel,
								maxHeightPixel - minHeightPixel);
						int sizeW = Math.min(max, oldWidth);// 计算宽度最大不能超过原bitmap的宽度
						int sizeH = Math.min(max, oldHeight);// 计算高度最大不能超过原bitmap的高度
						int minW = Math.min(minWidthPixel, oldWidth / 2 - sizeW
								/ 2);// 计算x轴最小像素,如果bitmap的x轴中点向左偏移最大边的1/2仍在有效范围内,则取该值,反之,取x轴上最先出现像素的位置.
						int minH = Math.min(minHeightPixel, oldHeight / 2
								- sizeH / 2);// 计算y轴最小像素,如果bitmap的y轴中点向上偏移最大边的1/2仍在有效范围内,则取该值,反之,取y轴上最先出现像素的位置.
						Bitmap bitmap = Bitmap.createBitmap(bm, minW, minH,
								sizeW, sizeH);
						iv_corpImage.setImageBitmap(bitmap);
