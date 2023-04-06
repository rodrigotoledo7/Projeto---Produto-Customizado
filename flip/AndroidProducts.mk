#Adiciona o nome do produto no lunch
COMMON_LUNCH_CHOICES := parkour_flip-eng

#Arquivo de configuração principal do produto
PRODUCT_MAKEFILES := $(LOCAL_DIR)/parkour_flip.mk

PRODUCT_COPY_FILES += \
	device/parkour/flip/default_wallpaper.png:product/media/wallpapers/default_wallpaper.png

PRODUCT_SYSTEM_PROPERTIES += \
	ro.parkour.name=Flip 
	
PRODUCT_PRODUCT_PROPERTIES += \
	ro.product.parkour.version=1.0 \
	ro.config.wallpaper=product/media/wallpapers/default_wallpaper.png 

PRODUCT_VENDOR_PROPERTIES += ro.vendor.parkour.hardware=ModelB \
