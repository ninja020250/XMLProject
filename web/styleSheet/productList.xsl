<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : productList.xsl
    Created on : July 4, 2019, 9:54 PM
    Author     : nhatc
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:ns2="http://www.monitors.com"
                xmlns:ns1="http://www.monitor.com">
    <xsl:output method="html" indent="yes"/>
    <xsl:param name="searchValue" />
    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <h3>Kết quả tìm kiếm của bạn</h3>
        <xsl:apply-templates/>
    </xsl:template>
    <xsl:template match="ns2:ListMonitor">
        <section class="products-container">
            <xsl:for-each select="ns1:Monitor[contains(translate(ns1:description,'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), $searchValue)]">
                <xsl:variable
                    name="model"
                    select="ns1:model">
                    <!-- Content:template -->
                </xsl:variable>               
                <div class="product-item" > 
                    <div class="overlay" onclick="goDetail('{$model}')">VIEW DETAIL</div>
                    <div class="field image">
                        <image src="{translate(ns1:imgURL, '\', '/')}" alt="hinh man hinh"/>
                    </div>
                    <div class="field title">
                        <xsl:value-of select="ns1:model" />
                    </div>
                    <div class="field price">
                        Giá:  <xsl:value-of select="ns1:price" />đ
                    </div>
                    <div class="field ">
                        Thương Hiệu:  <xsl:value-of select="ns1:brandName" />
                    </div>
                    <div class="field ">
                        Nhà Cung Cấp: <a href="{ns1:storeName}">
                            <xsl:value-of select="ns1:storeName" />
                        </a>
                    </div>
                    <div class="field">
                        <a href="{ns1:url}">
                            Link sản phẩm của Nhà Cung Cấp
                        </a>
                    </div>
                </div>
            </xsl:for-each>
        </section>
    </xsl:template>
</xsl:stylesheet>
