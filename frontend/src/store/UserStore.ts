import { create } from "zustand";
import { createJSONStorage, persist } from "zustand/middleware";

interface UserState {
    sessionId: string | null;
    setSessionId: (sessionId: string) => void;
    clearSessionId: () => void;
}

const useUserStore = create<UserState>()(
    persist(
        (set) => ({
            sessionId: null,
            setSessionId: (sessionId: string) => set({ sessionId }),
            clearSessionId: () => set({ sessionId: null }),
        }),
        {
            name: "user-storage",
            storage: createJSONStorage(() => localStorage),  
        }
    )
);

export default useUserStore;