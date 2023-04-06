# Herda as configurações do emulador (produto sdk_phone_x86_64)
$(call inherit-product, $(SRC_TARGET_DIR)/product/sdk_phone_x86_64.mk)

# Sobrescreve algumas variáveis com os dados do novo projeto
PRODUCT_NAME := parkour_flip
PRODUCT_DEVICE := flip
PRODUCT_BRAND := FlipBrand
PRODUCT_MODEL := FlipModel

PRODUCT_SYSTEM_PROPERTIES += ro.parkour.name=Flip

PRODUCT_PRODUCT_PROPERTIES += ro.product.parkour.version=1.0

PRODUCT_VENDOR_PROPERTIES += ro.vendor.parkour.hardware=ModelB

# copia o arquivo palomakoba.txt para o /system/etc da imagem do Android
PRODUCT_COPY_FILES += \
	device/parkour/flip/parkour.txt:system/etc/parkour.txt \
	device/parkour/flip/flip.rc:vendor/etc/init/flip.rc \
	device/parkour/flip/bootanimation.zip:product/media/bootanimation.zip 

# Seta o diretório de overlays
PRODUCT_PACKAGE_OVERLAYS = device/parkour/flip/overlay
PRODUCT_PACKAGES += \
	localiza