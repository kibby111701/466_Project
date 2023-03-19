library(readxl)
library(tidyverse)  # data manipulation
library(cluster)    # clustering algorithms
library(factoextra) # clustering visualization
library(dendextend) # for comparing two dendrograms
library(factoextra)
library(cluster)

wineData = read.csv(file= "wine-clustering.csv")
wineData = na.omit(wineData) # removes any missing value present in data
wineData = scale(wineData) # scaling/standardizing data 

eucDist = dist(wineData, method = "euclidean") # dissimilarity matrix 
manDist = dist(wineData, method = "manhattan")

# 1
hc1 <- hclust(eucDist, method = "single" ) # hierarchical clustering using single linkage
dend1 = as.dendrogram(hc1)
png("agg_single_dend.png",width=4000,height=1200)
par(mfrow = c(1,2), mar = c(5,2,1,0))
dend1 <- dend1 %>%
  color_branches(k = 3) %>%
  set("branches_lwd", c(2,1,2)) %>%
  set("branches_lty", c(1,2,1))

dend1 <- color_labels(dend1, k = 3)

plot(dend1, main="Agglomerative Clustering Dendrogram using Single Linkage")

dev.off()

# 2
hc2 = hclust(eucDist, method = "centroid" ) # hierarchical clustering using centroid linkage

dend2 = as.dendrogram(hc2)
png("agg_centroid_dend.png",width=4000,height=2200)
par(mfrow = c(1,2), mar = c(5,2,1,0))

dend2 <- dend2 %>%
  color_branches(k = 3) %>%
  set("branches_lwd", c(2,1,2)) %>%
  set("branches_lty", c(1,2,1))


plot(dend2, main="Agglomerative Clustering Dendrogram using Centroid Linkage")

dev.off()

# 3

hc3 <- hclust(manDist, method = "single" ) # hierarchical clustering using single linkage
dend3 = as.dendrogram(hc3)
png("agg_single_dend_man.png",width=4000,height=1200)
par(mfrow = c(1,2), mar = c(5,2,1,0))
dend3 <- dend3 %>%
  color_branches(k = 3) %>%
  set("branches_lwd", c(2,1,2)) %>%
  set("branches_lty", c(1,2,1))

plot(dend3, ylab= "Height")

dev.off()

# 4

hc4 <- hclust(manDist, method = "centroid" ) # hierarchical clustering using single linkage
dend4 = as.dendrogram(hc4)
png("agg_centroid_dend_man.png",width=4000,height=1200)
par(mfrow = c(1,2), mar = c(5,2,1,0))
dend4 <- dend4 %>%
  color_branches(k = 3) %>%
  set("branches_lwd", c(2,1,2)) %>%
  set("branches_lty", c(1,2,1))

plot(dend4)

dev.off()

# Gap Statistic 

gap_stat <- clusGap(wineData, FUN = hcut, nstart = 25, K.max = 10, B = 50) # calculate gap statistic for each number of clusters (up to 10 clusters)
fviz_gap_stat(gap_stat) # plot of clusters vs. gap statistic
