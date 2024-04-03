import { defineStore } from 'pinia';

export const useRecommendationStore = defineStore({
    id: 'recommendation',
    state: () => ({
        recommendationData: null,
    }),
    actions: {
        setRecommendationData(data) {
            this.recommendationData = data;
        },
    },
});